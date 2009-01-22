package com.jswiff.constants;

import com.jswiff.exception.InvalidCodeException;
import com.jswiff.exception.InvalidNameException;

public class TagConstants {

  public static enum ActionType implements ByteCodeConstant {
    /** End action */
    END( (short)0, "end"),
    /** Addition */
    ADD( (short)0x0a, "add"),
    /** Addition (as of SWF 5) */
    ADD_2( (short)0x47, "add2"),
    /** Boolean AND */
    AND( (short)0x10, "and"),
    /** ASCII to char conversion (deprecated as of SWF 5) */
    ASCII_TO_CHAR( (short)0x33, "asciitochar"),
    /** Bitwise AND */
    BIT_AND( (short)0x60, "bitand"),
    /** Bitwise left shift */
    BIT_L_SHIFT( (short)0x63, "bitlshift"),
    /** Bitwise OR */
    BIT_OR( (short)0x61, "bitor"),
    /** Bitwise right shift */
    BIT_R_SHIFT( (short)0x64, "bitrshift"),
    /** Bitwise unsigned right shift */
    BIT_U_R_SHIFT( (short)0x65, "biturshift"),
    /** Bitwise XOR */
    BIT_XOR( (short)0x62, "bitxor"),
    /** Execute script attached to a specified frame (deprecated since SWF 5) */
    CALL( (short)0x9e, "call"),
    /** Invoke a function */
    CALL_FUNCTION( (short)0x3d, "callfunction"),
    /** Invoke a method */
    CALL_METHOD( (short)0x52, "callmethod"),
    /** Type cast */
    CAST_OP( (short)0x2b, "castop"),
    /** Char to ASCII conversion (deprecated as of SWF 5) */
    CHAR_TO_ASCII( (short)0x32, "chartoascii"),
    /** Duplicate a sprite */
    CLONE_SPRITE( (short)0x24, "clonesprite"),
    /** Create new constant pool */
    CONSTANT_POOL( (short)0x88, "constantpool"),
    /** Decrement by one */
    DECREMENT( (short)0x51, "decrement"),
    /** Define a function */
    DEFINE_FUNCTION( (short)0x9b, "definefunction"),
    /** Define a function (as of SWF 7) */
    DEFINE_FUNCTION_2( (short)0x8e, "definefunction2"),
    /** Define and initialize local variable */
    DEFINE_LOCAL( (short)0x3c, "definelocal"),
    /** Define local variable */
    DEFINE_LOCAL_2( (short)0x41, "definelocal2"),
    /** Delete object property (to free memory) */
    DELETE( (short)0x3a, "delete"),
    /** Destroy object reference */
    DELETE_2( (short)0x3b, "delete2"),
    /** Divide two numbers */
    DIVIDE( (short)0x0d, "divide"),
    /** Ends drag operation, if any */
    END_DRAG( (short)0x28, "enddrag"),
    /** Push object's property names to stack */
    ENUMERATE( (short)0x46, "enumerate"),
    /** Push object's property names to stack (stack based argument passing) */
    ENUMERATE_2( (short)0x55, "enumerate2"),
    /** Test two numbers for equality */
    EQUALS( (short)0x0e, "equals"),
    /** Test two items for equality, takeing data types into account */
    EQUALS_2( (short)0x49, "equals2"),
    /** Create inheritance relationship between two classes */
    EXTENDS( (short)0x69, "extends"),
    /** Retrieve member value from object */
    GET_MEMBER( (short)0x4e, "getmember"),
    /** Return value of movie property */
    GET_PROPERTY( (short)0x22, "getproperty"),
    /** Get time since movie started playing */
    GET_TIME( (short)0x34, "gettime"),
    /** Get a specified URL */
    GET_URL( (short)0x83, "geturl"),
    /** Get contents from URL or exchange data with server */
    GET_URL_2( (short)0x9a, "geturl2"),
    /** Get variable value */
    GET_VARIABLE( (short)0x1c, "getvariable"),
    /** Go to specified frame */
    GO_TO_FRAME( (short)0x81, "gotoframe"),
    /** Go to specified frame (stack based) */
    GO_TO_FRAME_2( (short)0x9f, "gotoframe2"),
    /** Go to labeled frame */
    GO_TO_LABEL( (short)0x8c, "gotolabel"),
    /** Test if number is greater than another */
    GREATER( (short)0x67, "greater"),
    /** Evaluate condition */
    IF( (short)0x9d, "if"),
    /** Specifies interface a class implements */
    IMPLEMENTS_OP( (short)0x2c, "implementsop"),
    /** Decrement by one */
    INCREMENT( (short)0x50, "increment"),
    /** Create array and initialize it with stack values */
    INIT_ARRAY( (short)0x42, "initarray"),
    /** Create object and initialize it with stack values */
    INIT_OBJECT( (short)0x43, "initobject"),
    /** Determine if object is instance of a class */
    INSTANCE_OF( (short)0x54, "instanceof"),
    /** Unconditional branch to labeled action */
    JUMP( (short)0x99, "jump"),
    /** Tests if number is less than another */
    LESS( (short)0x0f, "less"),
    /** Tests if number is less than another, taking account of data types */
    LESS_2( (short)0x48, "less2"),
    /** Convert ASCII to multibyte char (deprecated as of SWF 5) */
    M_B_ASCII_TO_CHAR( (short)0x37, "mbasciitochar"),
    /** Convert multibyte char to ascii (deprecated as of SWF 5) */
    M_B_CHAR_TO_ASCII( (short)0x36, "mbchartoascii"),
    /** Extract substring from string (deprecated as of SWF 5) */
    M_B_STRING_EXTRACT( (short)0x35, "mbstringextract"),
    /** Compute string length (deprecated as of SWF 5) */
    M_B_STRING_LENGTH( (short)0x31, "mbstringlength"),
    /** Calculate remainder of division between two numbers */
    MODULO( (short)0x3f, "modulo"),
    /** Compute product of two numbers */
    MULTIPLY( (short)0x0c, "multiply"),
    /** Create new object (<code>NEW_OBJECT</code> is likely to be used instead) */
    NEW_METHOD( (short)0x53, "newmethod"),
    /** Create a new object, invoking a constructor */
    NEW_OBJECT( (short)0x40, "newobject"),
    /** Advance to next frame */
    NEXT_FRAME( (short)0x04, "nextframe"),
    /** Boolean NOT */
    NOT( (short)0x12, "not"),
    /** Boolean OR */
    OR( (short)0x11, "or"),
    /** Start playing at current frame */
    PLAY( (short)0x06, "play"),
    /** Remove top of stack */
    POP( (short)0x17, "pop"),
    /** Go back to previous frame */
    PREVIOUS_FRAME( (short)0x05, "previousframe"),
    /** Push at least one value to stack */
    PUSH( (short)0x96, "push"),
    /** Duplicate top of stack */
    PUSH_DUPLICATE( (short)0x4c, "pushduplicate"),
    /** Calculate random number */
    RANDOM_NUMBER( (short)0x30, "randomnumber"),
    /** Remove clone sprite */
    REMOVE_SPRITE( (short)0x25, "removesprite"),
    /** Return to calling function */
    RETURN( (short)0x3e, "return"),
    /** Populate object's member with given value */
    SET_MEMBER( (short)0x4f, "setmember"),
    /** Set movie property */
    SET_PROPERTY( (short)0x23, "setproperty"),
    /** Change context of subsequent actions (deprecated as of SWF 5) */
    SET_TARGET( (short)0x8b, "settarget"),
    /** Change context of subsequent actions (deprecated as of SWF 5) */
    SET_TARGET_2( (short)0x20, "settarget2"),
    /** Set variable value */
    SET_VARIABLE( (short)0x1d, "setvariable"),
    /** Swap the two items on top of stack */
    STACK_SWAP( (short)0x4d, "stackswap"),
    /** Make target sprite draggable */
    START_DRAG( (short)0x27, "startdrag"),
    /** Stop playing at current frame */
    STOP( (short)0x07, "stop"),
    /** Mute all playing sounds */
    STOP_SOUNDS( (short)0x09, "stopsounds"),
    /** Store top of stack into register */
    STORE_REGISTER( (short)0x87, "storeregister"),
    /** Check for equality taking data types into account */
    STRICT_EQUALS( (short)0x66, "strictequals"),
    /** Concatenate strings (deprecated since SWF 5) */
    STRING_ADD( (short)0x21, "stringadd"),
    /** Test strings for equality (deprecated since SWF 5) */
    STRING_EQUALS( (short)0x13, "stringequals"),
    /** Extract substring from string (deprecated as of SWF 5) */
    STRING_EXTRACT( (short)0x15, "stringextract"),
    /** Test whether string is greater than another */
    STRING_GREATER( (short)0x68, "stringgreater"),
    /** Compute string length (deprecated as of SWF 5) */
    STRING_LENGTH( (short)0x14, "stringlength"),
    /** Test whether string is less than another */
    STRING_LESS( (short)0x29, "stringless"),
    /** Compute difference between two numbers */
    SUBTRACT( (short)0x0b, "subtract"),
    /** Return target path of clip */
    TARGET_PATH( (short)0x45, "targetpath"),
    /** Throw an exception */
    THROW( (short)0x2a, "throw"),
    /** Toggle display quality (deprecated as of SWF 5) */
    TOGGLE_QUALITY( (short)0x08, "togglequality"),
    /** Convert item to integer */
    TO_INTEGER( (short)0x18, "tointeger"),
    /** Convert item to number */
    TO_NUMBER( (short)0x4a, "tonumber"),
    /** Convert item to string */
    TO_STRING( (short)0x4b, "tostring"),
    /** Send debugging output in test mode */
    TRACE( (short)0x26, "trace"),
    /** Define handlers for exceptions */
    TRY( (short)0x8f, "try"),
    /** Return item type */
    TYPE_OF( (short)0x44, "typeof"),
    /** Check if specified frame is loaded (deprecated as of SWF 5) */
    WAIT_FOR_FRAME( (short)0x8a, "waitforframe"),
    /** Check if specified frame is loaded (stack based, deprecated as of SWF 5) */
    WAIT_FOR_FRAME_2( (short)0x8d, "waitforframe2"),
    /** Define <code>with</code> action block */
    WITH( (short)0x94, "with"),
    /** Unknown Action */
    UNKNOWN_ACTION( (short)-2, "unknownaction");

    private static ByteCodeConstantHelper<ActionType> helper;

    static {
      helper = new ByteCodeConstantHelper<ActionType>(ActionType.values(), "Action Type");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static ActionType lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static ActionType lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private ActionType(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

  //undocumented tags
  // Debug tag: 63 (if enabling debug at saving, seems that EnableDebug2 and 63 are added)
  public static enum TagType implements ByteCodeConstant {
    /** Tag code for DefineBinaryData */
    DEBUG_ID( (short)63, "debugid"),
    /** Tag code for DefineBinaryData */
    DEFINE_BINARY_DATA( (short)87, "definebinarydata"),
    /** Tag code for DefineBits */
    DEFINE_BITS( (short)6, "definebits"),
    /** Tag code for DefineBitsJpeg2 */
    DEFINE_BITS_JPEG_2( (short)21, "definebitsjpeg2"),
    /** Tag code for DefineBitsJpeg3 */
    DEFINE_BITS_JPEG_3( (short)35, "definebitsjpeg3"),
    /** Tag code for DefineBitsLossless */
    DEFINE_BITS_LOSSLESS( (short)20, "definebitslossless"),
    /** Tag code for DefineBitsLossless2 */
    DEFINE_BITS_LOSSLESS_2( (short)36, "definebitslossless2"),
    /** Tag code for DefineButton */
    DEFINE_BUTTON( (short)7, "definebutton"),
    /** Tag code for DefineButton2 */
    DEFINE_BUTTON_2( (short)34, "definebutton2"),
    /** Tag code for DefineButtonCXform */
    DEFINE_BUTTON_C_XFORM( (short)23, "definebuttoncxform"),
    /** Tag code for DefineButtonSound */
    DEFINE_BUTTON_SOUND( (short)17, "definebuttonsound"),
    /** Tag code for DefineEditText */
    DEFINE_EDIT_TEXT( (short)37, "defineedittext"),
    /** Tag code for DefineFont */
    DEFINE_FONT( (short)10, "definefont"),
    /** Tag code for DefineFont2 */
    DEFINE_FONT_2( (short)48, "definefont2"),
    /** Tag code for DefineFont3 */
    DEFINE_FONT_3( (short)75, "definefont3"),
    /** Tag code for DefineFontInfo */
    DEFINE_FONT_INFO( (short)13, "definefontinfo"),
    /** Tag code for DefineFontInfo2 */
    DEFINE_FONT_INFO_2( (short)62, "definefontinfo2"),
    /** Tag code for DefineFontName */
    DEFINE_FONT_NAME( (short)88, "definefontname"),
    /** Tag code for FlashTypeSettings */
    FLASHTYPE_SETTINGS( (short)74, "flashtypesettings"),
    /** Tag code for DefineFontInfo3 */
    DEFINE_FONT_ALIGNMENT( (short)73, "definefontalignment"),
    /** Tag code for DefineMorphShape */
    DEFINE_MORPH_SHAPE( (short)46, "definemorphshape"),
    /** Tag code for DefineMorphShape2 */
    DEFINE_MORPH_SHAPE_2( (short)84, "definemorphshape2"),
    /** Tag code for DefineSceneFrameData */
    DEFINE_SCENE_FRAME_DATA( (short)86, "definesceneframedata"),
    /** Tag code for DefineShape */
    DEFINE_SHAPE( (short)2, "defineshape"),
    /** Tag code for DefineShape2 */
    DEFINE_SHAPE_2( (short)22, "defineshape2"),
    /** Tag code for DefineShape3 */
    DEFINE_SHAPE_3( (short)32, "defineshape3"),
    /** Tag code for DefineShape4 */
    DEFINE_SHAPE_4( (short)83, "defineshape4"),
    /** Tag code for DefineSound */
    DEFINE_SOUND( (short)14, "definesound"),
    /** Tag code for DefineSprite */
    DEFINE_SPRITE( (short)39, "definesprite"),
    /** Tag code for DefineText */
    DEFINE_TEXT( (short)11, "definetext"),
    /** Tag code for DefineText2 */
    DEFINE_TEXT_2( (short)33, "definetext2"),
    /** Tag code for DefineVideoStream */
    DEFINE_VIDEO_STREAM( (short)60, "definevideostream"),
    /** Tag code for DoABC */
    DO_ABC( (short)72, "doabc"),
    /** Tag code for DoABCDefine */
    DO_ABC_DEFINE( (short)82, "doabcdefine"),
    /** Tag code for DoAction */
    DO_ACTION( (short)12, "doaction"),
    /** Tag code for DoInitAction */
    DO_INIT_ACTION( (short)59, "doinitaction"),
    /** Tag code for EnableDebugger2 */
    ENABLE_DEBUGGER_2( (short)64, "enabledebugger2"),
    /** Tag code for EnableDebugger */
    ENABLE_DEBUGGER( (short)58, "enabledebugger"),
    /** Tag code for End tag (used internally) */
    END( (short)0, "end"),
    /** Tag code for ExportAssets */
    EXPORT_ASSETS( (short)56, "exportassets"),
    /** Tag code for FileAttributes */
    FILE_ATTRIBUTES( (short)69, "fileattributes"),
    /** Tag code for FrameLabel */
    FRAME_LABEL( (short)43, "framelabel"),
    /** Tag code for FreeCharacter */
    FREE_CHARACTER( (short)3, "freecharacter"),
    /** Tag code for ImportAssets */
    IMPORT_ASSETS( (short)57, "importassets"),
    /** Tag code for ImportAssets2 */
    IMPORT_ASSETS_2( (short)71, "importassets2"),
    /** Tag code for JpegTables */
    JPEG_TABLES( (short)8, "jpegtables"),
    /** Tag code for Metadata */
    METADATA( (short)77, "metadata"),
    /** Tag code for PlaceObject */
    PLACE_OBJECT( (short)4, "placeobject"),
    /** Tag code for PlaceObject2 */
    PLACE_OBJECT_2( (short)26, "placeobject2"),
    /** Tag code for PlaceObject3 */
    PLACE_OBJECT_3( (short)70, "placeobject3"),
    /** Tag code for ProductInfo */
    PRODUCT_INFO( (short)41, "productinfo"),
    /** Tag code for Protect */
    PROTECT( (short)24, "protect"),
    /** Tag code for RemoveObject */
    REMOVE_OBJECT( (short)5, "removeobject"),
    /** Tag code for RemoveObject2 */
    REMOVE_OBJECT_2( (short)28, "removeobject2"),
    /** Tag code for ScriptLimits */
    SCRIPT_LIMITS( (short)65, "scriptlimits"),
    /** Tag code for SetBackgroundColor */
    SET_BACKGROUND_COLOR( (short)9, "setbackgroundcolor"),
    /** Tag code for SetTabIndex */
    SET_TAB_INDEX( (short)66, "settabindex"),
    /** Tag code for ShowFrame */
    SHOW_FRAME( (short)1, "showframe"),
    /** Tag code for Scale9Grid */
    SCALE_9_GRID( (short)78, "scale9grid"),
    /** Tag code for SoundStreamBlock */
    SOUND_STREAM_BLOCK( (short)19, "soundstreamblock"),
    /** Tag code for SoundStreamHead */
    SOUND_STREAM_HEAD( (short)18, "soundstreamhead"),
    /** Tag code for SoundStreamHead2 */
    SOUND_STREAM_HEAD_2( (short)45, "soundstreamhead2"),
    /** Tag code for StartSound */
    START_SOUND( (short)15, "startsound"),
    /** Tag code for SymbolClass */
    SYMBOL_CLASS( (short)76, "symbolclass"),
    /** Tag code for VideoFrame */
    VIDEO_FRAME( (short)61, "videoframe"),
    /** Tag code for Unknown tag **/
    UNKNOWN_TAG( (short)-2, "unknowntag"),
    /** Tag code for malformed tag */
    MALFORMED( (short)-1, "malformedtag");

    private static ByteCodeConstantHelper<TagType> helper;

    static {
      helper = new ByteCodeConstantHelper<TagType>(TagType.values(), "Tag Type");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static TagType lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static TagType lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private TagType(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

  public static enum FillTypeGroup {
    SOLID, GRADIENT, BITMAP;
  }

  public static enum FillType implements ByteCodeConstant {
    SOLID(                      (short)0x00, FillTypeGroup.SOLID),
    LINEAR_GRADIENT(            (short)0x10, FillTypeGroup.GRADIENT),
    RADIAL_GRADIENT(            (short)0x12, FillTypeGroup.GRADIENT),
    FOCAL_RADIAL_GRADIENT(      (short)0x13, FillTypeGroup.GRADIENT),
    TILED_BITMAP(               (short)0x40, FillTypeGroup.BITMAP),
    CLIPPED_BITMAP(             (short)0x41, FillTypeGroup.BITMAP),
    NONSMOOTHED_TILED_BITMAP(   (short)0x42, FillTypeGroup.BITMAP),
    NONSMOOTHED_CLIPPED_BITMAP( (short)0x43, FillTypeGroup.BITMAP);

    private static ByteCodeConstantHelper<FillType> helper;

    static {
      helper = new ByteCodeConstantHelper<FillType>(FillType.values(), "Fill Type");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static FillType lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static FillType lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;
    private final FillTypeGroup group;

    FillType(short code, FillTypeGroup group) {
      this.code = code;
      this.group = group;
      this.name = this.name().replace("_", "").toLowerCase();
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    public FillTypeGroup getGroup() {
      return this.group;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  public static enum FilterType implements ByteCodeConstant {
    DROP_SHADOW(    (short)0),
    BLUR(           (short)1),
    GLOW(           (short)2),
    BEVEL(          (short)3),
    GRADIENT_GLOW(  (short)4),
    CONVOLUTION(    (short)5),
    COLOR_MATRIX(   (short)6),
    GRADIENT_BEVEL( (short)7);

    private static ByteCodeConstantHelper<FilterType> helper;

    static {
      helper = new ByteCodeConstantHelper<FilterType>(FilterType.values(), "Filter Type");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static FilterType lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static FilterType lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;

    private FilterType(short code) {
      this.code = code;
    }

    public short getCode() {
      return this.code;
    }

  }

  public static enum BlendMode implements ByteCodeConstant {
    NORMAL(     (short)1,  "normal"),
    LAYER(      (short)2,  "layer"), 
    MULTIPLY(   (short)3,  "multiply"), 
    SCREEN(     (short)4,  "screen"), 
    LIGHTEN(    (short)5,  "lighten"), 
    DARKEN(     (short)6,  "darken"), 
    DIFFERENCE( (short)7,  "difference"), 
    ADD(        (short)8,  "add"), 
    SUBTRACT(   (short)9,  "subtract"), 
    INVERT(     (short)10, "invert"), 
    ALPHA(      (short)11, "alpha"), 
    ERASE(      (short)12, "erase"), 
    OVERLAY(    (short)13, "overlay"), 
    HARD_LIGHT( (short)14, "hard light"); 

    private static ByteCodeConstantHelper<BlendMode> helper;

    static {
      helper = new ByteCodeConstantHelper<BlendMode>(BlendMode.values(), "Blend Mode");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static BlendMode lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static BlendMode lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private BlendMode(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

  public static enum SpreadMethod implements ByteCodeConstant {
    PAD(     (short)0, "pad"),
    REFLECT( (short)1, "reflect"), 
    REPEAT(  (short)2, "repeat"); 

    private static ByteCodeConstantHelper<SpreadMethod> helper;

    static {
      helper = new ByteCodeConstantHelper<SpreadMethod>(SpreadMethod.values(), "Spread Type");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static SpreadMethod lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static SpreadMethod lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private SpreadMethod(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

  public static enum InterpolationMethod implements ByteCodeConstant {
    RGB(        (short)0, "rgb"),
    LINEAR_RGB( (short)1, "linear-rgb"); 

    private static ByteCodeConstantHelper<InterpolationMethod> helper;

    static {
      helper = new ByteCodeConstantHelper<InterpolationMethod>(InterpolationMethod.values(), "Interpolation Method");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static InterpolationMethod lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static InterpolationMethod lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private InterpolationMethod(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

  /**
   * Identifies a spoken language that applies to text. Used by Flash
   * Player for line breaking of dynamic text and for choosing fallback fonts.
   */
  public static enum LangCode implements ByteCodeConstant {

    UNDEFINED(           (short)0, "undefined"),
    LATIN(               (short)1, "latin"),
    JAPANESE(            (short)2, "japanese"),
    KOREAN(              (short)3, "korean"),
    SIMPLIFIED_CHINESE(  (short)4, "simpchinese"),
    TRADITIONAL_CHINESE( (short)5, "tradchinese");

    private static ByteCodeConstantHelper<LangCode> helper;

    static {
      helper = new ByteCodeConstantHelper<LangCode>(LangCode.values(), "Language Code");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static LangCode lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static LangCode lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private LangCode(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

  public static enum ScaleStrokeMethod implements ByteCodeConstant {
    NONE(       (short)0, "none"),
    VERTICAL(   (short)1, "vertical"),
    HORIZONTAL( (short)2, "horizontal"),
    BOTH(       (short)3, "both");

    private static ByteCodeConstantHelper<ScaleStrokeMethod> helper;

    static {
      helper = new ByteCodeConstantHelper<ScaleStrokeMethod>(ScaleStrokeMethod.values(), "Scale Stroke");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static ScaleStrokeMethod lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static ScaleStrokeMethod lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private ScaleStrokeMethod(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

  public static enum CapStyle implements ByteCodeConstant {
    ROUND(  (short)0, "round"),
    NONE(   (short)1, "none"),
    SQUARE( (short)2, "square");

    private static ByteCodeConstantHelper<CapStyle> helper;

    static {
      helper = new ByteCodeConstantHelper<CapStyle>(CapStyle.values(), "Cap Style");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static CapStyle lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static CapStyle lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private CapStyle(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

  public static enum JointStyle implements ByteCodeConstant {
    ROUND( (short)0, "round"),
    BEVEL( (short)1, "bevel"),
    MITER( (short)2, "miter");

    private static ByteCodeConstantHelper<JointStyle> helper;

    static {
      helper = new ByteCodeConstantHelper<JointStyle>(JointStyle.values(), "Joint Style");
    }

    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static JointStyle lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }

    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static JointStyle lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
    }

    private final short code;
    private final String name;

    private JointStyle(short code, String name) {
      this.code = code;
      this.name = name;
    }

    public short getCode() {
      return this.code;
    }
    
    public String getNiceName() {
      return helper.getNiceName(this);
    }

    @Override
    public String toString() {
      return this.name;
    }

  }

}
