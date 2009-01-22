/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.jswiff.xml;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.jswiff.constants.TagConstants.ActionType;
import com.jswiff.constants.TagConstants.ValueType;
import com.jswiff.exception.InvalidNameException;
import com.jswiff.exception.MissingNodeException;
import com.jswiff.swfrecords.RegisterParam;
import com.jswiff.swfrecords.actions.*;
import com.jswiff.swfrecords.actions.Push.StackValue;
import com.jswiff.util.Base64;


class ActionXMLReader {
  
  static Action readAction(Element element) throws InvalidNameException {
    ActionType actionType = ActionType.lookup(element.getName());
    Action action;
    switch (actionType) {
    case ADD:
      action = new Add();
      break;
    case ADD_2:
      action = new Add2();
      break;
    case AND:
      action = new And();
      break;
    case ASCII_TO_CHAR:
      action = new AsciiToChar();
      break;
    case BIT_AND:
      action = new BitAnd();
      break;
    case BIT_L_SHIFT:
      action = new BitLShift();
      break;
    case BIT_OR:
      action = new BitOr();
      break;
    case BIT_R_SHIFT:
      action = new BitRShift();
      break;
    case BIT_U_R_SHIFT:
      action = new BitURShift();
      break;
    case BIT_XOR:
      action = new BitXor();
      break;
    case CALL:
      action = new Call();
      break;
    case CALL_FUNCTION:
      action = new CallFunction();
      break;
    case CALL_METHOD:
      action = new CallMethod();
      break;
    case CAST_OP:
      action = new CastOp();
      break;
    case CHAR_TO_ASCII:
      action = new CharToAscii();
      break;
    case CLONE_SPRITE:
      action = new CloneSprite();
      break;
    case CONSTANT_POOL:
      action = readConstantPool(element);
      break;
    case DECREMENT:
      action = new Decrement();
      break;
    case DEFINE_FUNCTION:
      action = readDefineFunction(element);
      break;
    case DEFINE_FUNCTION_2:
      action = readDefineFunction2(element);
      break;
    case DEFINE_LOCAL:
      action = new DefineLocal();
      break;
    case DEFINE_LOCAL_2:
      action = new DefineLocal2();
      break;
    case DELETE:
      action = new Delete();
      break;
    case DELETE_2:
      action = new Delete2();
      break;
    case DIVIDE:
      action = new Divide();
      break;
    case END_DRAG:
      action = new EndDrag();
      break;
    case ENUMERATE:
      action = new Enumerate();
      break;
    case ENUMERATE_2:
      action = new Enumerate2();
      break;
    case EQUALS:
      action = new Equals();
      break;
    case EQUALS_2:
      action = new Equals2();
      break;
    case EXTENDS:
      action = new Extends();
      break;
    case GET_MEMBER:
      action = new GetMember();
      break;
    case GET_PROPERTY:
      action = new GetProperty();
      break;
    case GET_TIME:
      action = new GetTime();
      break;
    case GET_URL:
      action = readGetURL(element);
      break;
    case GET_URL_2:
      action = readGetURL2(element);
      break;
    case GET_VARIABLE:
      action = new GetVariable();
      break;
    case GO_TO_FRAME:
      action = readGoToFrame(element);
      break;
    case GO_TO_FRAME_2:
      action = readGoToFrame2(element);
      break;
    case GO_TO_LABEL:
      action = readGoToLabel(element);
      break;
    case GREATER:
      action = new Greater();
      break;
    case IF:
      action = readIf(element);
      break;
    case IMPLEMENTS_OP:
      action = new ImplementsOp();
      break;
    case INCREMENT:
      action = new Increment();
      break;
    case INIT_ARRAY:
      action = new InitArray();
      break;
    case INIT_OBJECT:
      action = new InitObject();
      break;
    case INSTANCE_OF:
      action = new InstanceOf();
      break;
    case JUMP:
      action = readJump(element);
      break;
    case LESS:
      action = new Less();
      break;
    case LESS_2:
      action = new Less2();
      break;
    case M_B_ASCII_TO_CHAR:
      action = new MBAsciiToChar();
      break;
    case M_B_CHAR_TO_ASCII:
      action = new MBCharToAscii();
      break;
    case M_B_STRING_EXTRACT:
      action = new MBStringExtract();
      break;
    case M_B_STRING_LENGTH:
      action = new MBStringLength();
      break;
    case MODULO:
      action = new Modulo();
      break;
    case MULTIPLY:
      action = new Multiply();
      break;
    case NEW_METHOD:
      action = new NewMethod();
      break;
    case NEW_OBJECT:
      action = new NewObject();
      break;
    case NEXT_FRAME:
      action = new NextFrame();
      break;
    case NOT:
      action = new Not();
      break;
    case OR:
      action = new Or();
      break;
    case PLAY:
      action = new Play();
      break;
    case POP:
      action = new Pop();
      break;
    case PREVIOUS_FRAME:
      action = new PreviousFrame();
      break;
    case PUSH:
      action = readPush(element);
      break;
    case PUSH_DUPLICATE:
      action = new PushDuplicate();
      break;
    case RANDOM_NUMBER:
      action = new RandomNumber();
      break;
    case REMOVE_SPRITE:
      action = new RemoveSprite();
      break;
    case RETURN:
      action = new Return();
      break;
    case SET_MEMBER:
      action = new SetMember();
      break;
    case SET_PROPERTY:
      action = new SetProperty();
      break;
    case SET_TARGET:
      action = readSetTarget(element);
      break;
    case SET_TARGET_2:
      action = new SetTarget2();
      break;
    case SET_VARIABLE:
      action = new SetVariable();
      break;
    case STACK_SWAP:
      action = new StackSwap();
      break;
    case START_DRAG:
      action = new StartDrag();
      break;
    case STOP:
      action = new Stop();
      break;
    case STOP_SOUNDS:
      action = new StopSounds();
      break;
    case STORE_REGISTER:
      action = readStoreRegister(element);
      break;
    case STRICT_EQUALS:
      action = new StrictEquals();
      break;
    case STRING_ADD:
      action = new StringAdd();
      break;
    case STRING_EQUALS:
      action = new StringEquals();
      break;
    case STRING_EXTRACT:
      action = new StringExtract();
      break;
    case STRING_GREATER:
      action = new StringGreater();
      break;
    case STRING_LENGTH:
      action = new StringLength();
      break;
    case STRING_LESS:
      action = new StringLess();
      break;
    case SUBTRACT:
      action = new Subtract();
      break;
    case TARGET_PATH:
      action = new TargetPath();
      break;
    case THROW:
      action = new Throw();
      break;
    case TO_INTEGER:
      action = new ToInteger();
      break;
    case TO_NUMBER:
      action = new ToNumber();
      break;
    case TO_STRING:
      action = new ToString();
      break;
    case TOGGLE_QUALITY:
      action = new ToggleQuality();
      break;
    case TRACE:
      action = new Trace();
      break;
    case TRY:
      action = readTry(element);
      break;
    case TYPE_OF:
      action = new TypeOf();
      break;
    case WAIT_FOR_FRAME:
      action = readWaitForFrame(element);
      break;
    case WAIT_FOR_FRAME_2:
      action = readWaitForFrame2(element);
      break;
    case WITH:
      action = readWith(element);
      break;
    case UNKNOWN_ACTION:
      action = readUnknownAction(element);
      break;
    //case END:
    default:
      throw new AssertionError("Action type '" + actionType.name() + "' not handled!");
    }
    Attribute label = element.attribute("label");
    if (label != null) {
      action.setLabel(label.getValue());
    }
    return action;
  }

  @SuppressWarnings("unchecked")
  private static ConstantPool readConstantPool(Element element) {
    List constantElements     = element.elements();
    ConstantPool constantPool = new ConstantPool();
    List<String> constants    = constantPool.getConstants();
    for (Iterator it = constantElements.iterator(); it.hasNext();) {
      Element constantElement = (Element) it.next();
      Element valueElement = RecordXMLReader.getElement("value", constantElement);
      String content          = valueElement.getText();
      String constant         = (RecordXMLReader.getBooleanAttribute(
          "base64", valueElement)) ? Base64.decodeString(content) : content;
      constants.add(constant);
    }
    return constantPool;
  }

  @SuppressWarnings("unchecked")
  private static DefineFunction readDefineFunction(Element element) throws InvalidNameException {
    String name            = RecordXMLReader.getStringAttributeWithBase64Check(
        "name", element);
    List parameterElements = RecordXMLReader.getElement("parameters", element)
                                            .elements();
    int arrayLength        = parameterElements.size();
    String[] parameters    = new String[arrayLength];
    for (int i = 0; i < arrayLength; i++) {
      Element parameterElement = (Element) parameterElements.get(i);
      parameters[i] = RecordXMLReader.getStringAttributeWithBase64Check(
          "name", parameterElement);
    }
    DefineFunction defineFunction = new DefineFunction(name, parameters);
    RecordXMLReader.readActionBlock(defineFunction.getBody(), element);
    return defineFunction;
  }

  @SuppressWarnings("unchecked")
  private static DefineFunction2 readDefineFunction2(Element element) throws InvalidNameException {
    String name                = RecordXMLReader.getStringAttributeWithBase64Check(
        "name", element);
    short registerCount        = RecordXMLReader.getShortAttribute(
        "registercount", element);
    List parameterElements     = RecordXMLReader.getElement(
        "parameters", element).elements();
    int arrayLength            = parameterElements.size();
    RegisterParam[] parameters = new RegisterParam[arrayLength];
    for (int i = 0; i < arrayLength; i++) {
      Element parameterElement = (Element) parameterElements.get(i);
      String paramName         = RecordXMLReader.getStringAttributeWithBase64Check(
          "name", parameterElement);
      short register           = RecordXMLReader.getShortAttribute(
          "register", parameterElement);
      parameters[i]            = new RegisterParam(register, paramName);
    }
    DefineFunction2 defineFunction2 = new DefineFunction2(
        name, registerCount, parameters);
    Element preloadElement          = RecordXMLReader.getElement(
        "preload", element);
    if (RecordXMLReader.getBooleanAttribute("arguments", preloadElement)) {
      defineFunction2.preloadArguments();
    }
    if (RecordXMLReader.getBooleanAttribute("global", preloadElement)) {
      defineFunction2.preloadGlobal();
    }
    if (RecordXMLReader.getBooleanAttribute("parent", preloadElement)) {
      defineFunction2.preloadParent();
    }
    if (RecordXMLReader.getBooleanAttribute("root", preloadElement)) {
      defineFunction2.preloadRoot();
    }
    if (RecordXMLReader.getBooleanAttribute("super", preloadElement)) {
      defineFunction2.preloadSuper();
    }
    if (RecordXMLReader.getBooleanAttribute("this", preloadElement)) {
      defineFunction2.preloadThis();
    }
    Element suppressElement = RecordXMLReader.getElement("suppress", element);
    if (RecordXMLReader.getBooleanAttribute("arguments", suppressElement)) {
      defineFunction2.suppressArguments();
    }
    if (RecordXMLReader.getBooleanAttribute("super", suppressElement)) {
      defineFunction2.suppressSuper();
    }
    if (RecordXMLReader.getBooleanAttribute("this", suppressElement)) {
      defineFunction2.suppressThis();
    }
    RecordXMLReader.readActionBlock(defineFunction2.getBody(), element);
    return defineFunction2;
  }

  private static GetURL readGetURL(Element element) {
    String url    = RecordXMLReader.getStringAttributeWithBase64Check("url", element);
    String target = RecordXMLReader.getStringAttributeWithBase64Check("target", element);
    return new GetURL(url, target);
  }

  private static GetURL2 readGetURL2(Element element) {
    String sendVarsMethodString = RecordXMLReader.getStringAttribute(
        "sendvarsmethod", element);
    byte sendVarsMethod;
    if (sendVarsMethodString.equals("get")) {
      sendVarsMethod = GetURL2.METHOD_GET;
    } else if (sendVarsMethodString.equals("post")) {
      sendVarsMethod = GetURL2.METHOD_POST;
    } else if (sendVarsMethodString.equals("none")) {
      sendVarsMethod = GetURL2.METHOD_NONE;
    } else {
      throw new IllegalArgumentException(
        "Illegal sendvars method: " + sendVarsMethodString);
    }
    boolean loadTarget    = RecordXMLReader.getBooleanAttribute(
        "loadtarget", element);
    boolean loadVariables = RecordXMLReader.getBooleanAttribute(
        "loadvariables", element);
    return new GetURL2(sendVarsMethod, loadTarget, loadVariables);
  }

  private static GoToFrame readGoToFrame(Element element) {
    return new GoToFrame(RecordXMLReader.getIntAttribute("frame", element));
  }

  private static GoToFrame2 readGoToFrame2(Element element) {
    boolean play  = RecordXMLReader.getBooleanAttribute("play", element);
    int sceneBias = RecordXMLReader.getIntAttribute("scenebias", element);
    return new GoToFrame2(play, sceneBias);
  }

  private static GoToLabel readGoToLabel(Element element) {
    return new GoToLabel(
      RecordXMLReader.getStringAttributeWithBase64Check("framelabel", element));
  }

  private static If readIf(Element element) {
    String branchLabel = RecordXMLReader.getStringAttributeWithBase64Check("branchlabel", element);
    If ifAction = new If(branchLabel);
    if (ActionBlock.LABEL_OUT.equals(branchLabel)) {
      ifAction.setBranchOffset(RecordXMLReader.getShortAttribute("offset", element));
    }
    return ifAction;
  }

  private static Jump readJump(Element element) {
    String branchLabel = RecordXMLReader.getStringAttributeWithBase64Check("branchlabel", element);
    Jump jump = new Jump(branchLabel);
    if (ActionBlock.LABEL_OUT.equals(branchLabel)) {
      jump.setBranchOffset(RecordXMLReader.getShortAttribute("offset", element));
    }
    return jump;
  }

  @SuppressWarnings("unchecked")
  private static Push readPush(Element element) throws InvalidNameException {
    List valueElements = element.elements();
    Push push          = new Push();
    for (Iterator it = valueElements.iterator(); it.hasNext();) {
      Element valueElement  = (Element) it.next();
      ValueType type = ValueType.lookup(valueElement.getName());
      StackValue stackVal;
      switch (type) {
      case BOOLEAN:
        stackVal = StackValue.createBooleanValue(
            RecordXMLReader.getBooleanAttribute("value", valueElement));
        break;
      case CONSTANT_16:
        stackVal = StackValue.createConstant16Value(
            RecordXMLReader.getIntAttribute("id", valueElement));
        break;
      case CONSTANT_8:
        stackVal = StackValue.createConstant8Value(
            RecordXMLReader.getShortAttribute("id", valueElement));
        break;
      case DOUBLE:
        stackVal = StackValue.createDoubleValue(
            RecordXMLReader.getDoubleAttribute("value", valueElement));
        break;
      case FLOAT:
        stackVal = StackValue.createFloatValue(
            RecordXMLReader.getFloatAttribute("value", valueElement));
        break;
      case INTEGER:
        stackVal = StackValue.createIntegerValue(
            RecordXMLReader.getIntAttribute("value", valueElement));
        break;
      case NULL:
        stackVal = StackValue.createNullValue();
        break;
      case REGISTER:
        stackVal = StackValue.createRegisterValue(
            RecordXMLReader.getShortAttribute("number", valueElement));
        break;
      case STRING:
        stackVal = StackValue.createStringValue(
            RecordXMLReader.getStringAttributeWithBase64Check("value", valueElement));
        break;
      case UNDEFINED:
        stackVal = StackValue.createUndefinedValue();
        break;
        default :
          throw new AssertionError("Unhandled StackValue type '" + type.name() + "'");
      }
      push.addValue(stackVal);
    }
    return push;
  }

  private static SetTarget readSetTarget(Element element) {
    return new SetTarget(RecordXMLReader.getStringAttributeWithBase64Check("name", element));
  }

  private static StoreRegister readStoreRegister(Element element) {
    return new StoreRegister(
      RecordXMLReader.getShortAttribute("number", element));
  }

  private static Try readTry(Element element) throws InvalidNameException {
    Try tryAction;
    Attribute catchRegister = element.attribute("catchregister");
    if (catchRegister != null) {
      tryAction = new Try(Short.parseShort(catchRegister.getValue()));
    } else {
      Attribute catchVariable = element.attribute("catchvariable");
      if (catchVariable != null) {
        tryAction = new Try(catchVariable.getValue());
      } else {
        throw new MissingNodeException(
          "Neither catch register nor catch variable specified within try action!");
      }
    }
    RecordXMLReader.readActionBlock(
      tryAction.getTryBlock(), RecordXMLReader.getElement("try", element));
    Element catchElement = element.element("catch");
    if (catchElement != null) {
      RecordXMLReader.readActionBlock(tryAction.getCatchBlock(), catchElement);
    }
    Element finallyElement = element.element("finally");
    if (finallyElement != null) {
      RecordXMLReader.readActionBlock(
        tryAction.getFinallyBlock(), finallyElement);
    }
    return tryAction;
  }

  private static UnknownAction readUnknownAction(Element element) {
    return new UnknownAction(
      RecordXMLReader.getShortAttribute("code", element),
      Base64.decode(element.getText()));
  }

  private static WaitForFrame readWaitForFrame(Element element) {
    int frame       = RecordXMLReader.getIntAttribute("frame", element);
    short skipCount = RecordXMLReader.getShortAttribute("skipcount", element);
    return new WaitForFrame(frame, skipCount);
  }

  private static WaitForFrame2 readWaitForFrame2(Element element) {
    return new WaitForFrame2(
      RecordXMLReader.getShortAttribute("skipcount", element));
  }

  private static With readWith(Element element) throws InvalidNameException {
    With with = new With();
    RecordXMLReader.readActionBlock(with.getWithBlock(), element);
    return with;
  }
}
